package run.halo.starter.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class SyncRequest implements Serializable {

    private int type;

    private String url;
}