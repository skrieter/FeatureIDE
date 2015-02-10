/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2015  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 * 
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package de.ovgu.featureide.fm.ui.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.TableColumn;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import de.ovgu.featureide.fm.core.FunctionalInterfaces;
import de.ovgu.featureide.fm.ui.editors.IntelliCompletion.IntelliCompletionPopUpDialog.CompletionOptionModel;

/**
 * TODO description
 * 
 * @author Marcus Pinnecke
 */
public class IntelliCompletion {
	
	private IntelliCompletionPopUpDialog popupDialog = new IntelliCompletionPopUpDialog();
	
	public interface ICompletionOption {
		boolean showForInput(final String text, final int caretPosition);
		String getListText();
		String applyCompletion(final String text, final int caretPosition);
		Image getImage();
		Color getForeground();
		Color getBackground();
		float getProbability();
	}
	
	/**
	 * @param constraintTextLayout
	 */
	public IntelliCompletion(final Shell shell, final Control context) {
		enableAutoHideByContextFocusLost(shell, context);
	}
	
	public void registerOption(ICompletionOption option) {
		popupDialog.completionOptions.add(option);
	}

	private void enableAutoHideByContextFocusLost(final Shell shell, final Control context) {
		context.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				shell.getDisplay().timerExec(100, new Runnable() {
					
					@Override
					public void run() {
						if (!hasFocus())
							hide();
					}
				});				
			}
			
			@Override
			public void focusGained(FocusEvent e) {				
			}
		});
	}

	public void show(Point popupPoint) {
		if (!popupDialog.isOpen) {
			popupDialog.popupPoint = popupPoint;
			popupDialog.open();
		}
	}
	
	public void move(Point location) {
		if (popupDialog.isOpen) {
			popupDialog.getShell().setLocation(location);
		}
	}
	
	public void hide() {
	//	if (popupDialog.isOpen)
	//		popupDialog.close();
	}
	
	public boolean isShown() {
		return popupDialog.isOpen;
	}
	
	public boolean hasFocus() {
		return popupDialog.isFocused;
	}
	
	public void textChanged(String text, int caretPosition) {
		if (popupDialog.isOpen) {
			popupDialog.textChanged(text, caretPosition);
		}
	}
	
	public void selectNext() {
		if (popupDialog.isOpen) {
			if (popupDialog.tableViewer.getTable().getSelectionIndex() + 1 < popupDialog.tableViewer.getTable().getItemCount())
				popupDialog.tableViewer.getTable().setSelection(popupDialog.tableViewer.getTable().getSelectionIndex() + 1);
			else
				popupDialog.tableViewer.getTable().setSelection(0);
		}		
	}

	public void selectPrev() {
		if (popupDialog.isOpen) {
			if (popupDialog.tableViewer.getTable().getSelectionIndex() - 1 >= 0)
				popupDialog.tableViewer.getTable().setSelection(popupDialog.tableViewer.getTable().getSelectionIndex() - 1);
			else
				popupDialog.tableViewer.getTable().setSelection(popupDialog.tableViewer.getTable().getItemCount() - 1);
		}		
	}

	public String performSelectedAction(String text, int caretPosition) {
		if (popupDialog.isOpen) {
			int selIndex = popupDialog.tableViewer.getTable().getSelectionIndex(); 
			return ((CompletionOptionModel)popupDialog.tableViewer.getTable().getItem(selIndex).getData()).option.applyCompletion(text, caretPosition);
		}
		return null;
	}	


	class IntelliCompletionPopUpDialog extends PopupDialog {
		
		private boolean isOpen = false;
		private boolean isFocused = false;
		private Point popupPoint = new Point(0,0);
		private TableViewer tableViewer;
		private List<ICompletionOption> completionOptions = new ArrayList<>();
		
		final FocusListener focusListner = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				isFocused = false;
				close();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				isFocused = true;
			}
		};
				
		public IntelliCompletionPopUpDialog() {
			super(null, SWT.RESIZE | SWT.ON_TOP, false, false, false, false, false, null, null);
		}
		
		public class CompletionOptionModel {
			public ICompletionOption option;

			public CompletionOptionModel(ICompletionOption option) {
				this.option = option;
			}

			@Override
			public String toString() {
				return option.getListText();
			}

			public ICompletionOption getOption() {
				return option;
			}
		}		
		
		class IntelliCompletionLabelProvider implements ITableLabelProvider, ITableColorProvider {

			@Override
			public void addListener(ILabelProviderListener listener) {}

			@Override
			public void dispose() {}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			@Override
			public void removeListener(ILabelProviderListener listener) {
			}

			@Override
			public Color getForeground(Object element, int columnIndex) {
				return ((CompletionOptionModel)element).getOption().getForeground();
			}

			@Override
			public Color getBackground(Object element, int columnIndex) {
				return ((CompletionOptionModel)element).getOption().getForeground();
			}

			@Override
			public Image getColumnImage(Object element, int columnIndex) {
				return ((CompletionOptionModel)element).getOption().getImage();
			}

			@Override
			public String getColumnText(Object element, int columnIndex) {
				return ((CompletionOptionModel)element).getOption().getListText();
			}
			
		}
		
		@Override
		protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);
			
			//composite.addFocusListener(focusListner);
			
			FillLayout fillLayout = new FillLayout();
			fillLayout.type = SWT.SINGLE | SWT.VERTICAL | SWT.HORIZONTAL;
			composite.setLayout(fillLayout);
			
			tableViewer = new TableViewer(composite);
			tableViewer.setLabelProvider(new IntelliCompletionLabelProvider());
			tableViewer.setContentProvider(ArrayContentProvider.getInstance());
			tableViewer.getControl().setSize(600, 700);	
			
			tableViewer.getControl().addFocusListener(focusListner);
			
			tableViewer.getControl().addListener(SWT.Traverse, new Listener() {
				public void handleEvent(Event event) {
					if (event.detail == SWT.TRAVERSE_ESCAPE) {
						close();
					}
				}
			});
//			tableViewer.add
//			addFocusListener(focusListner);
//			
//			Text text = new Text(composite,SWT.SINGLE | SWT.BORDER);
//			text.setText("Hello World");
//			
//			text.addFocusListener(focusListner);
			return composite;
		}	
		
		@Override
		protected Point getInitialLocation(Point initialSize) {
			return popupPoint;
		}
		
		@Override
		protected Color getBackground() {
			return getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE);
		}
		
		@Override
		protected Point getDefaultSize() {
			return new Point(580, 200);
		}

		public void textChanged(String text, int caretPosition) {
			List<CompletionOptionModel> model = new ArrayList<CompletionOptionModel>();
			for (int i = 0; i < this.completionOptions.size(); i++)
				if (completionOptions.get(i).showForInput(text, caretPosition))
					model.add(new CompletionOptionModel(this.completionOptions.get(i)));
			
			if (model.isEmpty()) {
				close();
				return;
			}
			
			Collections.sort(model, new Comparator<CompletionOptionModel>() {

				@Override
				public int compare(CompletionOptionModel o1,
						CompletionOptionModel o2) {
					return o1.getOption().getListText().compareTo(o2.getOption().getListText());
				}
			
			});
			
			Collections.sort(model, new Comparator<CompletionOptionModel>() {

				@Override
				public int compare(CompletionOptionModel o1,
						CompletionOptionModel o2) {
					float p2 = o2.getOption().getProbability();
					float p1 = o1.getOption().getProbability();
					return (int) (p2 == p1? 0 : Math.signum(p2 - p1));   
				}
			});
			
			tableViewer.setInput(model.toArray(new CompletionOptionModel[model.size()]));
			tableViewer.getTable().setSelection(0);
		}
		
		@Override
		public int open() {
			isOpen = true;
			return super.open();
		}
		
		@Override
		public boolean close() {
			isOpen = false;
			return super.close();
		}

		
	}

	public void setFocus() {
		popupDialog.tableViewer.getControl().setFocus();
	}
	
}
