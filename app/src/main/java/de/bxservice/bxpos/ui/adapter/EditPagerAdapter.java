package de.bxservice.bxpos.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import de.bxservice.bxpos.R;
import de.bxservice.bxpos.logic.model.POSOrder;
import de.bxservice.bxpos.ui.fragment.OrderedItemsFragment;
import de.bxservice.bxpos.ui.fragment.OrderingItemsFragment;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 * Created by Diego Ruiz on 27/11/15.
 */
public class EditPagerAdapter extends FragmentPagerAdapter {

    public static final int ORDERING_POSITION = 0;
    public static final int ORDERED_POSITION  = 1;

    private Context context;
    private POSOrder order;

    public EditPagerAdapter(FragmentManager fm, Context context, POSOrder order) {
        super(fm);
        this.context = context;
        this.order = order;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case ORDERING_POSITION:
                return OrderingItemsFragment.newInstance(order);

            case ORDERED_POSITION:
                return OrderedItemsFragment.newInstance(order);

            default:
                return OrderedItemsFragment.newInstance(order);
        }

    }

    @Override
    public int getCount() {
        // Show 2 total pages. Ordered - Ordering.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case ORDERING_POSITION:
                return context.getResources().getString(R.string.ordering);

            case ORDERED_POSITION:
                return context.getResources().getString(R.string.ordered);

        }
        return null;
    }

}