package eu.europeana.dashboard.client.widgets;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.europeana.dashboard.client.DashboardWidget;
import eu.europeana.dashboard.client.Reply;
import eu.europeana.dashboard.client.dto.LanguageX;
import eu.europeana.dashboard.client.dto.RoleX;
import eu.europeana.dashboard.client.dto.TranslationX;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Management of translated strings
 *
 * @author Gerald de Jong, Beautiful Code BV, <geralddejong@gmail.com>
 */

public class TranslationWidget extends DashboardWidget {
    private Set<String> visibleLanguages = new HashSet<String>();
    private Button submitLanguageChoice;
    private Tree tree = new Tree();
    private DecoratorPanel treePanel = new DecoratorPanel();

    public TranslationWidget(World world) {
        super(world);
    }

    protected Widget createWidget() {
        treePanel.setWidget(new Label(world.messages().selectLanguages()));
        VerticalPanel p = new VerticalPanel();
        p.setSpacing(6);
        p.add(createLanguagesPanel());
        p.add(treePanel);
        if (world.user().getRole() == RoleX.ROLE_GOD) {
            final TextBox keyBox = new TextBox();
            Button addBox = new Button(world.messages().add());
            addBox.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    world.service().addMessageKey(keyBox.getText(), new Reply<Void>(){
                        public void onSuccess(Void result) {
                            keyBox.setText("");
                            refreshTree();
                        }
                    });
                }
            });
            Button removeBox = new Button(world.messages().delete());
            removeBox.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    world.service().removeMessageKey(keyBox.getText(), new Reply<Void>(){
                        public void onSuccess(Void result) {
                            keyBox.setText("");
                            refreshTree();
                        }
                    });
                }
            });
            HorizontalPanel hp = new HorizontalPanel();
            hp.setSpacing(10);
            hp.add(new Label(world.messages().messageKey()));
            hp.add(keyBox);
            hp.add(addBox);
            hp.add(removeBox);
            DecoratorPanel dp = new DecoratorPanel();
            dp.setWidget(hp);
            p.add(dp);
        }
        return p;
    }

    private Widget createLanguagesPanel() {
        final DecoratorPanel langPanel = new DecoratorPanel();
        world.service().fetchLanguages(new Reply<List<LanguageX>>() {
            public void onSuccess(List<LanguageX> result) {
                int rows = 2;
                int columns = result.size()/rows;
                if (result.size() % rows != 0) {
                    columns++;
                }
                Grid grid = new Grid(rows, columns);
                int row = 0;
                int column = 0;
                for (LanguageX languageX : result) {
                    final LanguageX language = languageX;
                    CheckBox checkBox = new CheckBox(language.getCode());
                    if (world.user().isLanguageAllowed(language.getCode(), false)) {
                        checkBox.setChecked(true);
                        checkBox.setStyleName("languageLabel");
                        visibleLanguages.add(language.getCode());
                    }
                    checkBox.addClickListener(new ClickListener() {
                        public void onClick(Widget sender) {
                            CheckBox box = (CheckBox)sender;
                            if (box.isChecked()) {
                                visibleLanguages.add(language.getCode());
                            }
                            else {
                                visibleLanguages.remove(language.getCode());
                            }
                        }
                    });
                    grid.setWidget(row, column, checkBox);
                    column++;
                    if (column == columns) {
                        column = 0;
                        row++;
                    }
                }
                submitLanguageChoice = new Button(world.messages().submit());
                submitLanguageChoice.addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {
                        refreshTree();
                    }
                });
                HorizontalPanel hp = new HorizontalPanel();
                hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
                hp.setSpacing(10);
                hp.add(grid);
                hp.add(submitLanguageChoice);
                langPanel.setWidget(hp);
            }
        });
        return langPanel;
    }

    private void refreshTree() {
        submitLanguageChoice.setEnabled(false);
        treePanel.setWidget(new Label(world.messages().loadingPleaseWait()));
        tree.removeItems();
        world.service().fetchTranslations(visibleLanguages, new Reply<Map<String,List<TranslationX>>>() {
            public void onSuccess(Map<String,List<TranslationX>> result) {
                List<String> sortedKeys = new ArrayList<String>(result.keySet());
                Collections.sort(sortedKeys);
                for (String key : sortedKeys) {
                    TreeItem keyItem = tree.addItem(key);
                    for (TranslationX translation : result.get(key)) {
                        keyItem.addItem(new KeyTranslation(keyItem, translation).getWidget());
                    }
                }
                submitLanguageChoice.setEnabled(true);
                treePanel.setWidget(tree);
            }
        });

    }

//        suggestPanel.add(new Label(world.messages().enterMessageKey()));

    private class KeyTranslation {
        private TreeItem keyTreeItem;
        private TranslationX translation;
        private TextBox translationBox;
        private Label langLabel = new Label();
        private HorizontalPanel buttonPanel = new HorizontalPanel();
        private Button submit;
        private Button reset;

        private KeyTranslation(TreeItem key, TranslationX translationValue) {
            this.keyTreeItem = key;
            this.translation = translationValue;
            langLabel.setText(translation.getLanguage().getCode());
            langLabel.setStyleName("languageLabel");
            buttonPanel.setVisible(false);
            translationBox = new TextBox();
            if (!world.user().isLanguageAllowed(translation.getLanguage().getCode(), true)) {
                translationBox.setEnabled(false);
            }
            translationBox.setStyleName("translationUnchanged");
            translationBox.setWidth("100%");
            translationBox.setText(translation.getValue());
            translationBox.addKeyboardListener(new KeyboardListenerAdapter() {
                @Override
                public void onKeyUp(Widget sender, char keyCode, int modifiers) {
                    checkChanged();
                }
            });
            submit = new Button(world.messages().translationSubmit());
            submit.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    world.service().setTranslation(keyTreeItem.getText(), translation.getLanguage().getCode(), translationBox.getText(), new Reply<TranslationX>() {
                        public void onSuccess(TranslationX result) {
                            translation = result;
                            checkChanged();
                        }
                    });
                }
            });
            submit.setEnabled(false);
            reset = new Button(world.messages().translationReset());
            reset.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    translationBox.setText(translation.getValue());
                    checkChanged();
                }
            });
            reset.setEnabled(false);
        }

        public Widget getWidget() {
            buttonPanel.setSpacing(4);
            buttonPanel.add(submit);
            buttonPanel.add(reset);
            DockPanel p = new DockPanel();
            p.setSpacing(5);
            p.setWidth("100%");
            p.add(langLabel,DockPanel.WEST);
            p.add(translationBox,DockPanel.CENTER);
            p.add(buttonPanel,DockPanel.EAST);
            p.setCellWidth(translationBox, "800px");
            p.setCellWidth(langLabel, "30px");
            p.setCellWidth(buttonPanel, "100px");
            return p;
        }

        private void checkChanged() {
            String boxText = translationBox.getText();
            String originalText = translation.getValue();
            boolean changed = !boxText.equals(originalText);
            if (changed) {
                translationBox.setStyleName("translationChanged");
            }
            else {
                translationBox.setStyleName("translationUnchanged");
            }
            submit.setEnabled(changed);
            reset.setEnabled(changed);
            buttonPanel.setVisible(changed);
        }
    }
}
