package com.restaurant.restaurant_management.serviceImp;

import com.restaurant.restaurant_management.dto.CategoryRequestDTO;
import com.restaurant.restaurant_management.dto.CategoryResponseDTO;
import com.restaurant.restaurant_management.dto.UserRegistrationDto;
import com.restaurant.restaurant_management.enums.RoleType;
import com.restaurant.restaurant_management.exception.ResourceNotFoundException;
import com.restaurant.restaurant_management.model.Category;
import com.restaurant.restaurant_management.model.Role;
import com.restaurant.restaurant_management.model.User;
import com.restaurant.restaurant_management.repository.CategoryRepository;
import com.restaurant.restaurant_management.repository.RoleRepository;
import com.restaurant.restaurant_management.repository.UserRepository;
import com.restaurant.restaurant_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        Category saved = categoryRepository.save(category);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        Category updated = categoryRepository.save(category);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    private CategoryResponseDTO mapToResponseDTO(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getName(), category.getDescription());
    }

    /**
     * Service class responsible for managing user registration and related operations.
     */
    @Service
    public static class UserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        /**
         * Registers a new user based on the provided registration data transfer object.
         * The user's password is encrypted before saving, and roles are assigned based
         * on the email domain suffix.
         *
         * @param registrationDto the DTO containing user registration information
         * @throws RuntimeException if the required role is not found in the database
         */
        public void registerUser(UserRegistrationDto registrationDto) {
            // Create a new User entity and set basic details
            User user = new User();
            user.setUsername(registrationDto.getUsername());
            user.setEmail(registrationDto.getEmail());

            // Encrypt the plain text password before saving
            String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
            user.setPassword(encodedPassword);

            // Determine user roles based on email domain
            Set<Role> assignedRoles = new HashSet<>();
            String email = registrationDto.getEmail();

            if (email.endsWith("@admin.com")) {
                // Assign ADMIN role if email ends with @admin.com
                Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
                assignedRoles.add(adminRole);
            } else if (email.endsWith("@staff.com")) {
                // Assign STAFF role if email ends with @staff.com
                Role staffRole = roleRepository.findByName(RoleType.ROLE_STAFF)
                        .orElseThrow(() -> new RuntimeException("STAFF role not found"));
                assignedRoles.add(staffRole);
            } else {
                // Assign CUSTOMER role by default
                Role customerRole = roleRepository.findByName(RoleType.ROLE_CUSTOMER)
                        .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));
                assignedRoles.add(customerRole);
            }

            // Set the roles on the user entity
            user.setRoles(assignedRoles);

            // Persist the new user in the database
            userRepository.save(user);
        }
    }
}
