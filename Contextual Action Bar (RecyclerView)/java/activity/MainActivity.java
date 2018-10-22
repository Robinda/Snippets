/**
Source : https://www.journaldev.com/15035/recyclerview-android-dividers-contextual-toolbar
*/

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ClickAdapterListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Model> dataModel;
    RecyclerViewAdapter mAdapter;
    ActionModeCallback actionModeCallback;
    ActionMode actionMode;
    FloatingActionButton fab;
    Boolean flagAllSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setVisibility(GONE);
                populateDataAndSetAdapter();
            }
        });

        flagAllSelected = false;

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        actionModeCallback = new ActionModeCallback();

        populateDataAndSetAdapter();
    }

    @Override
    public void onRowClicked(int position) {
        enableActionMode(position);
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }

    /**
     * Initialisation de l'ActionMode
     * @param position Position de l'item
     */
    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    /**
     * Gestion de la sélection / désélection d'un élément de la liste
     * @param position Position de l'élément dans la liste
     */
    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
            actionMode = null;
        }
        else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    /**
     * Sélection/désélection de tous les élements de la liste via le bouton "Select all"
     */
    private void selectAll() {
        // Si tout est déjà sélectionné, on déselectionne toute la liste
        if(flagAllSelected)
            mAdapter.clearSelections();
        else
            mAdapter.selectAll();

        int count = mAdapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
            actionMode = null;
        }
        else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }

        // On switch le flag
        flagAllSelected = !flagAllSelected;
    }

    /**
     * Callback de l'Action Mode
     */
    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Log.d("API123", "here");

            switch (item.getItemId()) {
                case R.id.action_delete:
                    // Suppression de toutes les lignes sélectionnées
                    deleteRows();
                    mode.finish();
                    return true;

                case R.id.action_color:
                    updateColoredRows();
                    mode.finish();
                    return true;

                case R.id.action_select_all:
                    selectAll();
                    return true;

                case R.id.action_refresh:
                    populateDataAndSetAdapter();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            actionMode = null;
        }
    }

    /**
     * Supprime les lignes sélectionnées
     */
    private void deleteRows() {
        List<Integer> selectedItemPositions = mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }

        mAdapter.notifyDataSetChanged();

        if (mAdapter.getItemCount() == 0)
            fab.setVisibility(View.VISIBLE);

        actionMode = null;
    }

    /**
     * Modifie la couleur du texte de l'item sélectionné
     */
    private void updateColoredRows() {
        List<Integer> selectedItemPositions = mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.updateData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();

        actionMode = null;
    }

    /**
     * Création des données
     */
    private void populateDataAndSetAdapter() {
        dataModel = new ArrayList<>();
        dataModel.add(new Model("Item 1", false));
        dataModel.add(new Model("Item 2", false));
        dataModel.add(new Model("Item 3", false));
        dataModel.add(new Model("Item 4", false));
        dataModel.add(new Model("Item 5", false));
        dataModel.add(new Model("Item 6", false));
        dataModel.add(new Model("Item 7", false));
        dataModel.add(new Model("Item 8", false));
        dataModel.add(new Model("Item 9", false));
        dataModel.add(new Model("Item 10", false));
        dataModel.add(new Model("Item 11", false));
        dataModel.add(new Model("Item 12", false));
        dataModel.add(new Model("Item 13", false));
        dataModel.add(new Model("Item 14", false));
        dataModel.add(new Model("Item 15", false));
        dataModel.add(new Model("Item 16", false));
        dataModel.add(new Model("Item 17", false));
        dataModel.add(new Model("Item 18", false));
        dataModel.add(new Model("Item 19", false));
        dataModel.add(new Model("Item 20", false));

        mAdapter = new RecyclerViewAdapter(this, dataModel, this);
        recyclerView.setAdapter(mAdapter);
    }
}
