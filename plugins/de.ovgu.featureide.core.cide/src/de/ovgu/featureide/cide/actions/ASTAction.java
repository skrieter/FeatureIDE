package de.ovgu.featureide.cide.actions;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class ASTAction implements IObjectActionDelegate {

	public ITextEditor activeEditor = null;

	public void run(IAction action) {

		ISelectionProvider selectionProvider = activeEditor.getSelectionProvider();
		ISelection selection = selectionProvider.getSelection();
		ITextSelection textSelection = (ITextSelection) selection;
		ITypeRoot typeRoot = JavaUI.getEditorInputTypeRoot(activeEditor.getEditorInput());
		ICompilationUnit icu = (ICompilationUnit) typeRoot.getAdapter(ICompilationUnit.class);
		CompilationUnit cu = parse(icu);
		NodeFinder finder = new NodeFinder(cu, textSelection.getOffset(), textSelection.getLength());
		ASTNode node = finder.getCoveringNode();
		if (node.getNodeType() == ASTNode.BLOCK) {
			Block decl = (Block) node;
			decl.accept(new ASTVisitor() {
				@Override
				public boolean visit(ExpressionStatement node) {

					return super.visit(node);
				}
			});
		}
		
		System.out.println("Node type: " + node.getNodeType());

		ICompilationUnit cun = (ICompilationUnit) EditorUtility.getEditorInputJavaElement(activeEditor, true);
		try {
			IJavaElement element = cun.getElementAt(textSelection.getOffset());
			if (element != null) {

			} else {
				System.out.println("No valid element selection");
			}

		} catch (JavaModelException e) {

			e.printStackTrace();
		}
	}

	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		if (targetPart instanceof ITextEditor) {
			activeEditor = (ITextEditor) targetPart;
		}
	}

}
