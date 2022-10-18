cmake_minimum_required(VERSION 3.1.3)
project(aoc-2021-03 CXX)

set(CMAKE_VERBOSE_MAKEFILE ON)

include(conanbuildinfo.cmake)
conan_basic_setup(TARGETS)

add_executable(${PROJECT_NAME} main.cc)
target_link_libraries(${PROJECT_NAME} CONAN_PKG::fmt)
set_target_properties(${PROJECT_NAME} PROPERTIES CXX_STANDARD 20)
