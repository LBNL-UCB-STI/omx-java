package omx.hdf5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class HDF5Loader {
    private static final String macos32 = "macosx32/libjhdf5.jnilib";
    private static final String macos64 = "macosx64/libjhdf5.jnilib";
    private static final String linux32 = "linux32/libjhdf5.so";
    private static final String linux64 = "linux64/libjhdf5.so";
    private static final String windows32 = "win32/jhdf5.dll";
    private static final String windows64 = "win64/jhdf5.dll";
    public static final String HDF5_LIB_PATH_PROPERTY = "ncsa.hdf.hdf5lib.H5.hdf5lib";

    public static void prepareHdf5Library() throws IOException {
        if (System.getProperty(HDF5_LIB_PATH_PROPERTY) != null) {
            return;
        }
        Path tempDir = Files.createTempDirectory("hdf5");
        tempDir.toFile().deleteOnExit();

        String libraryFileName = getLibraryFileName();
        String resource = "/hdf5-binaries/" + libraryFileName;
        Path libraryPath = tempDir.resolve(getLastFileName(libraryFileName));

        Files.copy(Objects.requireNonNull(HDF5Loader.class.getResourceAsStream(resource)), libraryPath);
        libraryPath.toFile().deleteOnExit();

        System.setProperty(HDF5_LIB_PATH_PROPERTY, libraryPath.toString());
    }

    private static String getLastFileName(String libraryFileName) {
        int slashIndex = libraryFileName.lastIndexOf('/');
        return libraryFileName.substring(slashIndex + 1);
    }

    private static String getLibraryFileName() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            boolean is64bit;
            if (os.contains("windows"))
                is64bit = System.getenv("ProgramFiles(x86)") != null;
            else
                is64bit = System.getProperty("os.arch").contains("64");

            if (is64bit)
                return windows64;
            else
                return windows32;
        }

        if (os.contains("mac")) {
            String arch = System.getProperty("os.arch");
            if (arch.contains("aarch64") || arch.endsWith("_64"))
                return macos64;
            else
                return macos32;
        }

        if (os.contains("nix") || os.contains("nux"))
            if (System.getProperty("os.arch").endsWith("64"))
                return linux64;
            else
                return linux32;

        throw new RuntimeException(String.format("Os %s is not supported by hdf5", os));
    }
}
