# Mercury

[![DOI](https://zenodo.org/badge/21578/byu-vv-lab/mercury.svg)](https://zenodo.org/badge/latestdoi/21578/byu-vv-lab/mercury)

Mercury is a tool for verifying message passing programs. It generates potential send/receive match-pairs and analyzes
them to find potential deadlocks. Input for the program is a CTP written in the grammar defined in
[the antlr4 source](src/main/antlr4/edu/byu/cs/vv/Parser/CTPParser.g4).

The application can be built using `mvn compile` and `mvn test`. Because we require the
[Z3 theorem prover](https://github.com/Z3Prover/z3), running the application requires the compiled library.
The library compiled for OSX is included in the distribution. Other platforms will require compiling Z3 and adding
the library to the `libs` directory.