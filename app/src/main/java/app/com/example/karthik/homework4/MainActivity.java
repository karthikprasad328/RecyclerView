package app.com.example.karthik.homework4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance(R.id.action_home))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, PlaceholderFragment.newInstance(id)).commit();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        MovieData movieData=new MovieData();

        public PlaceholderFragment() {
        }

        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
            movieData=new MovieData();
        }

        public static PlaceholderFragment newInstance(int sectionNumber)
        {
            PlaceholderFragment placeholderFragment= new PlaceholderFragment();
            Bundle args= new Bundle();
            args.putInt("section number", sectionNumber);
            placeholderFragment.setArguments(args);
            return placeholderFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView;
            int options = getArguments().getInt("section number");
           switch (options) {
               case R.id.action_recyclerview:
               rootView = inflater.inflate(R.layout.layout_recyclerview, container, false);
               RecyclerView mRecylerView = (RecyclerView) rootView.findViewById(R.id.cardList);

               LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

               mRecylerView.setLayoutManager(mLayoutManager);
               mRecylerView.setHasFixedSize(true);

               final MyRecyclerViewAdapter mRecylerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList());
               mRecylerView.setAdapter(mRecylerViewAdapter);

               mRecylerViewAdapter.setOnClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                   @Override
                   public void OnItemClick(View v, int position) {
                       HashMap<String, ?> itemMap = (HashMap<String, ?>) mRecylerViewAdapter.getItem(position);
                       TextView title = (TextView) v.findViewById(R.id.movietitle);
                       String name = title.getText().toString();
                       Toast.makeText(getActivity(), "Selected: " + name, Toast.LENGTH_SHORT).show();

                   }

                   @Override
                   public void OnItemLongClick(View v, int position) {
                       movieData.addItem(position, (HashMap) ((HashMap) movieData.getItem(position).clone()));
                       mRecylerViewAdapter.notifyItemInserted(position);
                   }
               });

               Button selectall = (Button) rootView.findViewById(R.id.selectallbutton);
               selectall.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       for (int i = 0; i < mRecylerViewAdapter.getItemCount(); i++) {
                           HashMap<String, Boolean> selectionval = (HashMap<String, Boolean>) movieData.getItem(i);
                           selectionval.put("selection", true);
                       }
                       mRecylerViewAdapter.notifyDataSetChanged();
                   }
               });

               Button clearall = (Button) rootView.findViewById(R.id.clearallbutton);
               clearall.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       for (int i = 0; i < mRecylerViewAdapter.getItemCount(); i++) {
                           HashMap<String, Boolean> selectionval = (HashMap<String, Boolean>) movieData.getItem(i);
                           selectionval.put("selection", false);
                       }
                       mRecylerViewAdapter.notifyDataSetChanged();
                   }
               });

               Button deletebutton = (Button) rootView.findViewById(R.id.deletebutton);
               deletebutton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       for (int i = mRecylerViewAdapter.getItemCount() - 1; i >= 0; i--) {
                           HashMap<String, Boolean> movie = (HashMap<String, Boolean>) movieData.getItem(i);
                           boolean b = movie.get("selection");
                           if (b == true) {
                               movieData.moviesList.remove(i);
                               mRecylerViewAdapter.notifyItemRemoved(i);
                           }


                       }
                   }
               });
                break;
               case R.id.action_home:
                   rootView=inflater.inflate(R.layout.fragment_main, container, false);
                    break;

              default: rootView=inflater.inflate(R.layout.fragment_main, container, false);
                  break;
           }

            return rootView;
        }
    }
}
