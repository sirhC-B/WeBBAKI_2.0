package de.thb.webbaki.mail.confirmation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getConfirmationToken(String confirmationToken){
        return confirmationTokenRepository.findByToken(confirmationToken);
    }

    public int setConfirmedAt(String token){
        return confirmationTokenRepository.setConfirmedAt(token, LocalDateTime.now());
    }
}
