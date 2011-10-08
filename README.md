## What is GRUJ?

GRUJ is an application that downloads and launches jar files. Its primary function is to reduce the binary footprint of source versioned projects and releases to a minimum, due to its tiny size (~3kb).

GRUJ was written in Jasmin, and the source code is freely available. This is free and unencumbered software released into the public domain.

## Why use GRUJ?

Although there are many cross-platform tools that would do the job, such as wget, curl and similar the main problem is that none of these is available by default on all platforms.
Since GRUJ is primarily designed to bootstrap other Java programs, the only thing you need is a JRE itself.

Same goes for calculating checksums. Again, there is a lot of tools available, but if you want to write a bash/batch script and make sure it's solid, you need to make sure that whatever digest engine you used is already installed.
GRUJ can double as a poor man's checksum verifier, since it can generate MD5 and SHA-1 hashes. In short – it's a couple of simple functions related to downloading and verifying wrapped in one small package.

## How does GRUJ work?

+ Check to see if file was already downloaded before
  + optionally check the cached file against a checksum
  + optionally delete the invalidated file on checksum mismatch
+ If file did not exist, download it from the provided URL
  + optionally compare the newly downloaded file to a checksum
+ Run the jar file by invoking its main-class method
  + optionally run a different main method
