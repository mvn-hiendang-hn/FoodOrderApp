package hiendtt21020315.uet.mobile.admin.list_history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import hiendtt21020315.uet.mobile.R;


public class DeliveringFragment extends Fragment {
    private Invoce_DAO dao;
    private ArrayList<Invoice> list;

    private Dekivering_Adapter adapter;
    RecyclerView recyclerView;

    public DeliveringFragment() {
        // Required empty public constructor
    }


    public static DeliveringFragment newInstance(String param1, String param2) {
        DeliveringFragment fragment = new DeliveringFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =view.findViewById(R.id.invoice_ry_dang_giao);
        Invoce_DAO dao = new Invoce_DAO(getContext());
        list = dao.SeLectDangGiao();
        Collections.reverse(list);
        adapter = new Dekivering_Adapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        Invoce_DAO dao = new Invoce_DAO(getContext());
        list = dao.SeLectDangGiao();
        Collections.reverse(list);
        adapter = new Dekivering_Adapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivering, container, false);
    }
}