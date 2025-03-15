package run.halo.starter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import run.halo.starter.service.impl.CnblogsSynchronizer;
import run.halo.starter.service.impl.CsdnSynchronizer;

@AllArgsConstructor
@Component
public class SynchronizerFactory {

    private final CsdnSynchronizer csdnSynchronizer;
    private final CnblogsSynchronizer cnblogsSynchronizer;

    public BlogSynchronizer getSynchronizer(int platform) {
        return switch (platform) {
            case 0 -> csdnSynchronizer;
            case 1 -> cnblogsSynchronizer;
            default -> throw new IllegalArgumentException("Unsupported platform: " + platform);
        };
    }
}