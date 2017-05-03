package bollor.likesinstagram;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import layout.Hashtag;
import layout.InfoPage;
import layout.Like;

/**
 * Created by david on 03/05/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Like tab1 = new Like();
                return tab1;
            case 1:
                Hashtag tab2 = new Hashtag();
                return tab2;
            case 2:
                InfoPage tab3 = new InfoPage();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
