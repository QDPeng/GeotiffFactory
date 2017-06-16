cmake_minimum_required(VERSION 3.4.1)
project(tifftobmp)

#set(CMAKE_BUILD_TYPE "Debug")

# set tiff library src files
set(
    LOCAL_TIFF_SRC_FILES

    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_dirread.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_zip.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_flush.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_next.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_ojpeg.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_dirwrite.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_dirinfo.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_dir.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_compress.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_close.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_tile.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_open.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_getimage.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_pixarlog.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_warning.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_dumpmode.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_jpeg.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_jbig.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_predict.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/mkg3states.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_write.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_error.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_version.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_print.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_color.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_read.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_extension.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_thunder.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_lzw.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_fax3.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_luv.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_codec.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_unix.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_packbits.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_aux.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_fax3sm.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_swab.c
    ${PROJECT_SOURCE_DIR}/tiff/libtiff/tif_strip.c
    ${PROJECT_SOURCE_DIR}/tiff/port/lfind.c )

#  -fprefetch-loop-arrays
set(CMAKE_C_FLAGS "-DAVOID_TABLES -O3 -fstrict-aliasing")

find_library( # Sets the name of the path variable.
              log-lib
              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )


# generate libtiff.so
add_library( # Specifies the name of the library.
             tiff
             # Sets the library as a shared library.
             SHARED
             # Provides a relative path to your source file(s).
             ${LOCAL_TIFF_SRC_FILES} )
# add static lib
set(
  jpeg
  ${PROJECT_SOURCE_DIR}/libs/${ANDROID_ABI}/libjpeg.a )
# add static lib
set(
  png
  ${PROJECT_SOURCE_DIR}/libs/${ANDROID_ABI}/libpng.a )

# link static jpeg lib and system libz.so zip
target_link_libraries(tiff ${jpeg} -lz)

#=============tiff factory==========
set(
    TIFF_FACTORY_SRC_FILES

    ${PROJECT_SOURCE_DIR}/NativeExceptions.cpp
    ${PROJECT_SOURCE_DIR}/NativeTiffBitmapFactory.cpp
    ${PROJECT_SOURCE_DIR}/NativeDecoder.cpp )

# generate libtifffactory.so
add_library( # Specifies the name of the library.
             tifffactory
             # Sets the library as a shared library.
             SHARED
             # Provides a relative path to your source file(s).
             ${TIFF_FACTORY_SRC_FILES} )

#link tiff log dl jnigraphics
target_link_libraries(tifffactory tiff ${log-lib} -ldl -ljnigraphics)

#=============tiff saver==========
set(
    TIFF_SAVER_SRC_FILES

    ${PROJECT_SOURCE_DIR}/NativeExceptions.cpp
   	${PROJECT_SOURCE_DIR}/NativeTiffSaver.cpp )

# generate libtiffsaver.so
add_library( # Specifies the name of the library.
             tiffsaver
             # Sets the library as a shared library.
             SHARED
             # Provides a relative path to your source file(s).
             ${TIFF_SAVER_SRC_FILES} )

#link tiff log dl jnigraphics
target_link_libraries(tiffsaver tiff ${log-lib} -ljnigraphics -ldl)

#=============tiff converter==========
 set(
    TIFF_CONVERTER_SRC_FILES

    ${PROJECT_SOURCE_DIR}/NativeExceptions.cpp
    ${PROJECT_SOURCE_DIR}/NativeTiffConverter.cpp
    ${PROJECT_SOURCE_DIR}/TiffToPngConverter.cpp
    ${PROJECT_SOURCE_DIR}/TiffToJpgConverter.cpp
    ${PROJECT_SOURCE_DIR}/BaseTiffConverter.cpp
    ${PROJECT_SOURCE_DIR}/PngToTiffConverter.cpp
    ${PROJECT_SOURCE_DIR}/JpgToTiffConverter.cpp )

#set(LIBRARY_DEPS android log z)

 # generate libtiffconverter.so
 add_library( # Specifies the name of the library.
              tiffconverter
              # Sets the library as a shared library.
              SHARED
              # Provides a relative path to your source file(s).
              ${TIFF_CONVERTER_SRC_FILES} )
#link tiff jpeg.a png.a log zip dl jnigraphics
target_link_libraries(tiffconverter tiff ${jpeg} ${png} ${log-lib} -lz -ldl -ljnigraphics)

# Specifies a path to tiff header files.
include_directories(
    ${PROJECT_SOURCE_DIR}
    ${PROJECT_SOURCE_DIR}/tiff/libtiff
    ${PROJECT_SOURCE_DIR}/jpeg
    ${PROJECT_SOURCE_DIR}/png )

set(CMAKE_BUILD_TYPE Release)