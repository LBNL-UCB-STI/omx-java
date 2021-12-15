
Java API for [OMX](https://github.com/osPlanning/omx)

# Examples
There are two examples:

```
java -classpath build/libs/omx-2.0.0.jar omx.OMXTest

java -classpath build/libs/omx-2.0.0.jar omx.OMXFile
```

# Jar packaging

HDF5 java and native libraries are packed with omx.jar. If your platform is not presented in `resources/hdf5-binaries`
you need to put there the appropriate library and modify `HDF5Loader` class.

Before you start working with OMX you need to call
```
HDF5Loader.prepareHdf5Library();
```
It stores an appropriate native library to your `temp` directory and allows HDF5 library to load it.

## HDF5 libraries that was used

[HD5 2.8](http://www.hdfgroup.org/ftp/HDF5/releases/HDF-JAVA/HDF-JAVA-2.8/bin/) libraries.

# Maven coordinates

```
com.github.LBNL-UCB-STI:omx-java:version
```
where version is the latest release tag (i.e. `v2.0.0`)

