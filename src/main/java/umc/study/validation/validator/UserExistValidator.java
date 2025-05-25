package umc.study.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.study.apiPayload.code.status.ErrorStatus;
import umc.study.repository.UserRepository.UserRepository;
import umc.study.validation.annotation.ExistUser;

@Component
@RequiredArgsConstructor
public class UserExistValidator implements ConstraintValidator<ExistUser, Long> {

    private final UserRepository userRepository;

    @Override
    public void initialize(ExistUser constraintAnnotation) { }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        boolean isValid = userRepository.existsById(value);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.MEMBER_NOT_FOUND.getMessage()).addConstraintViolation();
        }
        return isValid;
    }
}