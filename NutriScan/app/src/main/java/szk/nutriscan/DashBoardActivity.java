package szk.nutriscan;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.res.Configuration;

//menu action items


public class DashBoardActivity extends Activity {

    // For the dashboard drawer list items
    private String[] drawerTitles;
    public ListView drawerList;
    //DrawerLayout variable
    private DrawerLayout drawerLayout;
    // DrawerLayout ActionBar Toggle button
    private ActionBarDrawerToggle drawerToggle;

    //ActionBar title Sync
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // Creating the ListView using ArrayAdapter
        drawerTitles = getResources().getStringArray(
                R.array.dashboard_list);
        //DrawerLayout set
        drawerLayout = (DrawerLayout) findViewById(
                R.id.activity_dashboard_drawer);

        drawerList = (ListView) findViewById(R.id.activity_dashboard_drawer_listview);
        drawerList.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1,
                drawerTitles
        ));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //Action Bar DrawerLayout Toggle Button
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,R.drawable.list_toggle_icon, R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        // remove this if doubt - enable up icon
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //ActionBar title Sync
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0);
        }

        // For actionBAr Sync
        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        if(fragment instanceof HomeFragment) {
                            currentPosition = 0;
                        }
                        if(fragment instanceof ScanFragment) {
                            currentPosition = 1;
                        }
                        if(fragment instanceof AddMealFragment) {
                            currentPosition = 2;
                        }
                        if(fragment instanceof MonthFragment) {
                            currentPosition = 3;
                        }
                        setActionBarTitle(currentPosition);
                        drawerList.setItemChecked(currentPosition, true);
                    }
                }
        );

    }


    //Sync actionbar state with actionbar toggle
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    //pass config changes to actionbar toggle
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    // called when invalidateOptionMenu method is called Hides the Action Bar items when drawer opened
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_account).setVisible(!drawerOpen);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    // for drawer item click listener
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            // Runs when items in list are clicked
            selectItem(position);
        }

    }

    private void selectItem(int position) {
        currentPosition = position;
        Fragment fragment;
        switch(position) {

            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ScanFragment();
                break;
            case 2:
                fragment = new AddMealFragment();
                break;
            case 3:
                fragment = new MonthFragment();
                break;

            default:
                fragment = new HomeFragment();
        }

        FragmentTransaction ft = getFragmentManager()
                .beginTransaction();
        ft.replace(R.id.activity_dashboard_drawer_framelayout,
                fragment, "visible_fragment");
        //ft.addToBackStack(null);          To avoid blank activity (*** UNTESTED ***)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        //setting Actionbar Title
        setActionBarTitle(position);
        //close the drawer
        drawerLayout.closeDrawer(drawerList);
    }

    private void setActionBarTitle(int position) {

        String title;
        title = drawerTitles[position];
        getActionBar().setTitle(title);
    }

    // for ActionBAr back sync
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }



    //menu action items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu to action bar view
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //menu action items reaction
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Actionbar toggle handle being clicked
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch(item.getItemId()) {
            // for account
            case R.id.action_account:
                Intent accountIntent = new Intent(this, AccountActivity.class);
                startActivity(accountIntent);
                return true;

            //for settings page
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
