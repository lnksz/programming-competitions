CXX = g++
CPPFLAGS = -Wall -Wextra -fsanitize=address,undefined -ggdb -Og

all:
	$(CXX) $(CPPFLAGS) main.cpp && ./a.out <sample
clean:
	rm -f a.out
diff:
	$(CXX) $(CPPFLAGS) main.cpp && time ./a.out <sample | diff -yZ - output
