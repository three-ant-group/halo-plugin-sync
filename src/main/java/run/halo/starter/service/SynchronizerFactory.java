package run.halo.starter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import run.halo.starter.model.ContentRequest;
import run.halo.starter.service.impl.AliCloudSynchronizer;
import run.halo.starter.service.impl.CnblogsSynchronizer;
import run.halo.starter.service.impl.CsdnSynchronizer;
import run.halo.starter.service.impl.HuaWeiCloudSynchronizer;
import run.halo.starter.service.impl.JianShuSynchronizer;
import run.halo.starter.service.impl.OsChinaSynchronizer;
import run.halo.starter.service.impl.TencentCloudSynchronizer;
import run.halo.starter.service.impl.ZhiHuSynchronizer;

@AllArgsConstructor
@Component
public class SynchronizerFactory {

    private final CsdnSynchronizer csdnSynchronizer;
    private final CnblogsSynchronizer cnblogsSynchronizer;
    private final JianShuSynchronizer jianShuSynchronizer;
    private final OsChinaSynchronizer osChinaSynchronizer;
    private final ZhiHuSynchronizer zhiHuSynchronizer;
    private final HuaWeiCloudSynchronizer huaWeiCloudSynchronizer;
    private final AliCloudSynchronizer aliCloudSynchronizer;
    private final TencentCloudSynchronizer tencentCloudSynchronizer;

    public BlogSynchronizer getSynchronizer(int platform) {
        return switch (platform) {
            case 0 -> csdnSynchronizer;
            case 1 -> cnblogsSynchronizer;
            case 2 -> jianShuSynchronizer;
            case 3 -> osChinaSynchronizer;
            case 4 -> zhiHuSynchronizer;
            case 5 -> huaWeiCloudSynchronizer;
            case 6 -> aliCloudSynchronizer;
            case 7 -> tencentCloudSynchronizer;
            default -> throw new IllegalArgumentException("Unsupported platform: " + platform);
        };
    }
}