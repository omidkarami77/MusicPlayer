package project.com.maktab.musicplayer;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.com.maktab.musicplayer.model.Song;
import project.com.maktab.musicplayer.model.SongLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongsRecyclerFragment extends Fragment {
    RecyclerView mSongsRv;
    private List<Song> mSongList;
    private static final String STATUS_ARGS = "status_args";
    private static final String ID_ARGS = "id_args";
    private RecyclerViewAdapter mAdapter;
    private String listPicker;
    private Long id;

    public static SongsRecyclerFragment newInstance(String status, Long id) {

        Bundle args = new Bundle();
        args.putString(STATUS_ARGS, status);
        args.putLong(ID_ARGS, id);
        SongsRecyclerFragment fragment = new SongsRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SongsRecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPicker = getArguments().getString(STATUS_ARGS, "");
        id = getArguments().getLong(ID_ARGS, 0);
        mSongList = SongLab.getInstance().getSongList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.recycler_view, container, false);
        mSongsRv = view.findViewById(R.id.recycler_view);

        mSongsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (listPicker.equalsIgnoreCase("album"))
            mAdapter = new RecyclerViewAdapter(SongLab.getInstance().getSongListByAlbum(getActivity(), id));
        else if (listPicker.equalsIgnoreCase("artist"))
            mAdapter = new RecyclerViewAdapter(SongLab.getInstance().getSongListByArtist(getActivity(), id));

        else
            mAdapter = new RecyclerViewAdapter(mSongList);

        mSongsRv.setHasFixedSize(true);

        mSongsRv.setAdapter(mAdapter);


        return view;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mCoverIv;
        private TextView mSongTv;
        private TextView mArtistTv;
        private Song mSong;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mCoverIv = itemView.findViewById(R.id.cover_image);
            mSongTv = itemView.findViewById(R.id.songs_name_tv);
            mArtistTv = itemView.findViewById(R.id.artist_name_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = PlayerActivity.newIntent(getActivity(), mSong.getId());
                    startActivity(intent);
                }
            });

        }

        public void bind(Song song) {
            mSong = song;
            mCoverIv.setImageBitmap(song.getBitmap());
            mSongTv.setText(song.getTitle());
            mArtistTv.setText(song.getArtist());
        }

    }


    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        private List<Song> mSongList;

        public RecyclerViewAdapter(List<Song> songList) {
            mSongList = songList;
        }

        public void setSongList(List<Song> songList) {
            mSongList = songList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.songs_list_item, viewGroup, false);
            RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
            Song song = mSongList.get(i);
            recyclerViewHolder.bind(song);
        }

        @Override
        public int getItemCount() {
            return mSongList.size();
        }
    }

}
