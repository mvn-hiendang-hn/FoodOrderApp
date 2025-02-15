package hiendtt21020315.uet.mobile.user.history;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hiendtt21020315.uet.mobile.admin.list_history.DaThanhToanFragment;
import hiendtt21020315.uet.mobile.admin.list_history.DeliveringFragment;
import hiendtt21020315.uet.mobile.admin.list_history.ListHistoryFragment;

public class History_QuanLy_Adapter extends FragmentStateAdapter {
    public History_QuanLy_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HistoryFragment();
            case 1:
                return new DangChuanBiHangFragment();
            case 2:
                return new DangGiaoFragment();
            case 3:
                return new History_DaThanhToanFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
