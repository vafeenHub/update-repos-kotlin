package data.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

internal interface GitHubRawContentService {
    @Streaming
    @GET("{accountName}/{repoName}/{default_branch}/{fileName}")
    suspend fun getRawContent(
        @Path("accountName") accountName: String,
        @Path("repoName") repoName: String,
        @Path("default_branch") defaultBranch: String,
        @Path("fileName") fileName: String
    ): ResponseBody
}