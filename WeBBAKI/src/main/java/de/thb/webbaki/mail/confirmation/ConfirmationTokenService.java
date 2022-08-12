package de.thb.webbaki.mail.confirmation;

import de.thb.webbaki.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findByToken(confirmationToken);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.setConfirmedAt(token, LocalDateTime.now());
    }

    public User getUserById(long id){
        return confirmationTokenRepository.getUserById(id);
    }

    public boolean enabledByUser(String token) {
        ConfirmationToken confirmationToken = getConfirmationToken(token);

        if (!confirmationToken.getUserConfirmation()) {
            confirmationToken.setUserConfirmation(true);
            return true;
        } else
            return false;
    }

    public boolean enabledByAdmin(String token) {
        ConfirmationToken confirmationToken = getConfirmationToken(token);

        if (!confirmationToken.getAdminConfirmation()) {
            confirmationToken.setAdminConfirmation(true);
            return true;
        }else
            return false;
    }
}
