import io.github.sandydunlop.markista.spi.DocService;

module markista.docagrams {
    requires markista;

    provides DocService with io.github.sandydunlop.markista.docservice.docagrams.Docagrams;
}
