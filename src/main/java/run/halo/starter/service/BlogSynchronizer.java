package run.halo.starter.service;

import cn.hutool.json.JSONUtil;
import run.halo.starter.constants.CommonConstants;
import run.halo.starter.model.PostRequest;
import run.halo.starter.model.SyncResult;
import run.halo.starter.util.HttpUtils;
import java.io.IOException;


public interface BlogSynchronizer {

    default SyncResult<String> sync(String url,String token,String session,String origin){
        try {
            PostRequest postRequest = parse(url);
            String jsonStr = JSONUtil.toJsonStr(postRequest);

            HttpUtils.postJson(origin+ CommonConstants.POST_API,jsonStr,token,session);

            return SyncResult.success("已成功导入文章");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return SyncResult.failure("文章解析失败: " + e.getMessage());
        }
    }

    PostRequest parse(String url) throws IOException;
}