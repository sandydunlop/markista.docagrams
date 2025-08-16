package io.github.sandydunlop.markista.docservice.docagrams.structure;

import java.util.ArrayList;

import io.github.sandydunlop.markista.model.ModuleNode;
import io.github.sandydunlop.markista.model.PackageNode;
import io.github.sandydunlop.markista.model.TypeView;

public class ModuleTree extends AbstractTree {
    public ModuleTree() {
        // Nothing to see here
    }

    public void scan() {
        y = 0;
        contents = new ArrayList<>();
        root = addModule(module, null);
        for (TreeNode treeNode : contents) {
            if (treeNode.getLabel().length() > imageWidth) {
                imageWidth = treeNode.getLabel().length() ;
            }
        }
        imageWidth = (imageWidth + 2) * 12;
        imageHeight = y;
    }

    private TreeNode addModule(ModuleNode node, TreeNode current) {
        String name = node.getName().isBlank() ? "unnamed module" : node.getName();
        TreeNode treeNode = new TreeNode(NodeKind.MODULE, name);
        if (current != null) {
            current.addChild(treeNode);
            treeNode.setX(current.getX() + indent);
            treeNode.setY(y);
            treeNode.setParent(current);
        }
        y += lineHeight;
        contents.add(treeNode);
        for (PackageNode pkg : node.getPackages()) {
            if (pkg instanceof PackageNode pn) {
                addPackage(pn, treeNode);
            }
        }
        if (node.hasModuleInfo()) {
            addType("module-info", treeNode);
        }
        return treeNode;
    }

    private void addPackage(PackageNode node, TreeNode current) {
        TreeNode treeNode = new TreeNode(NodeKind.PACKAGE, node.getQualifiedName());
        if (current != null) {
            current.addChild(treeNode);
            treeNode.setX(current.getX() + indent);
            treeNode.setY(y);
            treeNode.setParent(current);
        }
        y += lineHeight;
        contents.add(treeNode);
        for (PackageNode pkg : node.getPackages()) {
            if (pkg instanceof PackageNode pn) {
                addPackage(pn, treeNode);
            }
        }
        if (node.hasPackageInfo()) {
            addType("package-info", treeNode);
        }
        for (TypeView type : node.getTypes()) {
            addType(type.getSimpleName(), treeNode);
        }
    }

    private void addType(String name, TreeNode current) {
        TreeNode treeNode = new TreeNode(NodeKind.CODE, name);
        if (current != null) {
            current.addChild(treeNode);
            treeNode.setX(current.getX() + indent);
            treeNode.setY(y);
            treeNode.setParent(current);
        }
        y += lineHeight;
        contents.add(treeNode);
    }
}
