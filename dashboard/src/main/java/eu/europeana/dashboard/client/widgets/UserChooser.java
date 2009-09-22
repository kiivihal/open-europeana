package eu.europeana.dashboard.client.widgets;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestionEvent;
import com.google.gwt.user.client.ui.SuggestionHandler;
import com.google.gwt.user.client.ui.Widget;
import eu.europeana.dashboard.client.DashboardWidget;
import eu.europeana.dashboard.client.Reply;
import eu.europeana.dashboard.client.dto.UserX;

import java.util.ArrayList;
import java.util.List;

/**
 * A widget for choosing users
 *
 * @author Gerald de Jong, Beautiful Code BV, <geralddejong@gmail.com>
 */

public class UserChooser extends DashboardWidget {
    private UserX selectedUser;
    private SuggestBox suggestBox;
    private Listener listener;

    public UserChooser(World world) {
        super(world);
    }

    public interface Listener {
        void userSelected(UserX user);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public UserX getSelectedUser() {
        return selectedUser;
    }

    protected Widget createWidget() {
        HorizontalPanel panel = new HorizontalPanel();
        suggestBox = new SuggestBox(new UserOracle());
        suggestBox.addEventHandler(new SuggestionHandler() {
            public void onSuggestionSelected(SuggestionEvent event) {
                UserSuggestion suggestion = (UserSuggestion)event.getSelectedSuggestion();
                selectedUser = suggestion.user;
                if (listener != null) {
                    listener.userSelected(selectedUser);
                }
            }
        });
        suggestBox.setWidth("300px");
        panel.setSpacing(5);
        panel.add(new Label(world.messages().identifyUserPrompt()));
        panel.add(suggestBox);
        return panel;
    }

    public void clear() {
        suggestBox.setText("");
    }

    private static class UserSuggestion implements SuggestOracle.Suggestion {
        private UserX user;

        private UserSuggestion(UserX user) {
            this.user = user;
        }

        public String getDisplayString() {
            return user.getUserName()+" <"+user.getEmail()+">";
        }

        public String getReplacementString() {
            return getDisplayString();
        }
    }

    private class UserOracle extends SuggestOracle {
        private boolean busy = false;

        @Override
        public void requestSuggestions(final Request request, final Callback callback) {
            if (busy) return;
            String query = request.getQuery();
            busy = true;
            world.service().fetchUsers(query, new Reply<List<UserX>>() {
                @Override
                public void onFailure(Throwable caught) {
                    busy = false;
                    super.onFailure(caught);
                }
                public void onSuccess(List<UserX> result) {
                    List<UserSuggestion> suggestions = new ArrayList<UserSuggestion>();
                    for (UserX user : result) {
                        suggestions.add(new UserSuggestion(user));
                    }
                    busy = false;
                    Response response = new Response(suggestions);
                    callback.onSuggestionsReady(request, response);
                }
            });
        }
    }
}