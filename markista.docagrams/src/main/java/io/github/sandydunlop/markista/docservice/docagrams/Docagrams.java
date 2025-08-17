package io.github.sandydunlop.markista.docservice.docagrams;

import io.github.sandydunlop.markista.core.Configuration;
import io.github.sandydunlop.markista.core.Context;
import io.github.sandydunlop.markista.docservice.docagrams.structure.Structures;
import io.github.sandydunlop.markista.model.Api;
import io.github.sandydunlop.markista.model.ModuleNode;
import io.github.sandydunlop.markista.spi.DocService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

public class Docagrams implements DocService {
    /// The API that is passed in from Markista for processing.
    Api api;

    /// Context provides reporting and access to the documentation
    /// directory.
    Context ctx;

    public Docagrams() {
        // Nothing to see here
    }

    public void setContext(Context context) {
        ctx = context;
    }

    /// Lets Markista know that this extension does not replace
    /// Markista's built-int default Markdown DocService.
    @Override
    public boolean replacesDefault() {
        return false;
    }

    /// Called before the default DocService is run.
    @Override
    public boolean start(Api api, Context context) {
        this.api = api;
        this.ctx = context;
        return true;
    }

    /// Called after the default DocService is run.
    @Override
    public boolean finish() {
        ctx.reportInfo("Running Docagrams");
        // save(Path.of(ctx.getOutputDirectory(),"api.data"));
        createDiagrams();
        return true;
    }

    public void createDiagrams() {
        Structures structureWriter = new Structures();
        ModuleNode moduleNode = api.getModules().getFirst();
        structureWriter.setModule(moduleNode);
        try {
            structureWriter.run();
        } catch(IOException e) {
            ctx.reportError("Error creating diagrams: " + e.getMessage());
        }
    }

    public void save(Path path) { 
        try (FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
                ObjectOutputStream objectOutputStream  = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(api);
            objectOutputStream.flush();
        } catch (IOException e) {
            ctx.reportError("Error saving API: " + e.getMessage());
        }
    }
    
    public void load(Path path) {
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile());
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            api = (Api) objectInputStream.readObject();
            ctx.reportInfo("Loaded " + api.getName());
        } catch (IOException | ClassNotFoundException e) {
            ctx.reportError("Error loading API: " + e.getMessage());
        }
    }

    /// main method for debugging only, allows the Docagrams 
    /// extension to run without Markista.
    public static void main(String[] args) {
        Docagrams docagrams = new Docagrams();
        LocalReporter reporter = new LocalReporter();
        Context ctx = Context.getInstance();
        ctx.setReporter(reporter);
        ctx.setOutputDirectory("build");

        if (args != null && args.length > 0) {
            String fileName = args[0];
            Configuration.setProjectPath("../markista");
            docagrams.setContext(ctx);
            docagrams.load(Path.of(fileName));
            docagrams.createDiagrams();
        }
    }
}
