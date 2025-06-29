package com.example.xplorestylefilemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilePaneFragment extends Fragment {

    private static final String ARG_PATH = "arg_path";
    private String currentPath;
    private ListView listView;

    public static FilePaneFragment newInstance(String path) {
        FilePaneFragment fragment = new FilePaneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PATH, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file_pane, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView = view.findViewById(R.id.file_list_view);
        if (getArguments() != null) {
            currentPath = getArguments().getString(ARG_PATH);
            loadFiles(currentPath);
        }
    }

    private void loadFiles(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        List<String> fileNames = new ArrayList<>();

        if (dir.getParent() != null)
            fileNames.add("..");

        if (files != null) {
            Arrays.sort(files);
            for (File f : files) {
                fileNames.add(f.getName() + (f.isDirectory() ? "/" : ""));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, fileNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            String selected = fileNames.get(position);
            File nextFile = selected.equals("..") ? new File(path).getParentFile() : new File(path, selected);
            if (nextFile != null && nextFile.isDirectory()) {
                currentPath = nextFile.getAbsolutePath();
                loadFiles(currentPath);
            }
        });
    }
}
