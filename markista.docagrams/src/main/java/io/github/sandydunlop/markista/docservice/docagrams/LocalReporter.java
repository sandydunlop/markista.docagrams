package io.github.sandydunlop.markista.docservice.docagrams;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;

import com.sun.source.util.DocTreePath;

import jdk.javadoc.doclet.Reporter;

public class LocalReporter implements Reporter {

    @Override
    public void print(Kind kind, String message) {
        System.out.println(String.format("%s: %s", kind, message));
    }

    @Override
    public void print(Kind kind, DocTreePath path, String message) {
        System.out.println(String.format("%s: %s", kind, message));
    }

    @Override
    public void print(Kind kind, Element element, String message) {
        System.out.println(String.format("%s: %s", kind, message));
    }

}
