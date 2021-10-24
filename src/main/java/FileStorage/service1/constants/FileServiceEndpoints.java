package FileStorage.service1.constants;

public class FileServiceEndpoints {

    public static final String API_BASE = "/api/";

    public static final String DOWNLOAD_ONE_FILE_V1 = API_BASE + "file/{filename}/v1";

    public static final String UPLOAD_FILES_V1 = API_BASE + "upload-files/v1";

    public static final String GET_LIST_OF_USERS = API_BASE + "user-folders/v1";

    public static final String CREATE_SUBFOLDER = API_BASE + "folder/v1";

    public static final String DELETE_ALL_FILES_V1 = API_BASE + "bucket-files/v1";

    public static final String DELETE_FILES_V1 = API_BASE + "files/v1";

    public static final String GET_LIST_FILES_V1 = API_BASE + "file-info/v1";

    public static final String DOWNLOAD_ZIP_V1 = API_BASE + "files/v1";

    private FileServiceEndpoints() {
    }
}
