package com.example.droid.viewmodel.github;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import rx.Subscription;

import com.example.droid.R;
import com.example.droid.viewmodel.IViewModel;
import com.example.droid.model.github.Repository;


/**
 * ViewModel for the RepositoryActivity
 */
public class RepositoryViewModel implements IViewModel {

    private static final String TAG = "RepositoryViewModel";
    private Repository repository;
    private Context context;
    private Subscription subscription;

    public ObservableField<String> ownerName;
    public ObservableField<String> ownerEmail;
    public ObservableField<String> ownerLocation;
    public ObservableInt ownerEmailVisibility;
    public ObservableInt ownerLocationVisibility;
    public ObservableInt ownerLayoutVisibility;

    public RepositoryViewModel(Context context, final Repository repository) {
        this.repository = repository;
        this.context = context;
        this.ownerName = new ObservableField<>();
        this.ownerEmail = new ObservableField<>();
        this.ownerLocation = new ObservableField<>();
        this.ownerLayoutVisibility = new ObservableInt(View.INVISIBLE);
        this.ownerEmailVisibility = new ObservableInt(View.VISIBLE);
        this.ownerLocationVisibility = new ObservableInt(View.VISIBLE);
        // Trigger loading the rest of the user data as soon as the view model is created.
        // It's odd having to trigger this from here. Cases where accessing to the data model
        // needs to happen because of a change in the Activity/Fragment lifecycle
        // (i.e. an activity created) don't work very well with this MVVM pattern.
        // It also makes this class more difficult to test. Hopefully a better solution will be found
        loadFullUser(repository.owner.url);
    }

    public String getDescription() {
        return repository.description;
    }

    public String getHomepage() {
        return repository.homepage;
    }

    public int getHomepageVisibility() {
        return repository.hasHomepage() ? View.VISIBLE : View.GONE;
    }

    public String getLanguage() {
        return context.getString(R.string.text_language, repository.language);
    }

    public int getLanguageVisibility() {
        return repository.hasLanguage() ? View.VISIBLE : View.GONE;
    }

    public int getForkVisibility() {
        return repository.isFork() ? View.VISIBLE : View.GONE;
    }

    public String getOwnerAvatarUrl() {
        return repository.owner.avatarUrl;
    }

    @Override
    public void destroy() {
        this.context = null;
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
    }



    private void loadFullUser(String url) {
      /*  ApiClient apiClient = ApiClient.getInstance(context);

        subscription = apiClient.getService().userFromUrl(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(apiClient.defaultSubscribeScheduler())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Log.i(TAG, "Full user data loaded " + user);
                        ownerName.set(user.name);
                        ownerEmail.set(user.email);
                        ownerLocation.set(user.location);
                        ownerEmailVisibility.set(user.hasEmail() ? View.VISIBLE : View.GONE);
                        ownerLocationVisibility.set(user.hasLocation() ? View.VISIBLE : View.GONE);
                        ownerLayoutVisibility.set(View.VISIBLE);
                    }
                });*/
    }
}
