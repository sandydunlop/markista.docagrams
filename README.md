# Docagrams

Docagrams is a [DocService](https://sandydunlop.github.io/markista/javadoc/markista/spi/DocService/)
extension to the [Markista](https://sandydunlop.github.io/markista/) markdown doclet
that produces Java module and directury structure diagrams in
[SVG](https://www.w3.org/Graphics/SVG/) format.


## Getting Started

Docagram's source is available from its [GitHub repository](https://github.com/sandydunlop/markista.docagrams), and binaries are available on its [releases](https://github.com/sandydunlop/markista.docagrams/releases) page.

When running Markista as your Javadoc doclet, ensure the Docagrams JAR file is in your module path. That's it - Markista will automatically load and run it. Diagrams will be written to a `diagrams` folder inside your project's documentation folder.

## Sample Output

Docagrams produces three diagrams. Samples are shown below.

### Java Module Structure

![](https://sandydunlop.github.io/markista/assets/trees/module.svg)

### Source Tree

![](https://sandydunlop.github.io/markista/assets/trees/source.svg)

### Documentation Structure

![](https://sandydunlop.github.io/markista/assets/trees/docs.svg)
