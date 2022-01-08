package ndk.utils_android16.widgets.pass_book;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter;
import ndk.utils_android1.DateUtils1;
import ndk.utils_android16.DoubleUtils;
import ndk.utils_android16.Float_Utils;
import ndk.utils_android16.models.sortable_tableView.pass_book.PassBookEntryV2;

import static android.graphics.Color.BLACK;

public class Pass_Book_TableView_Data_Adapter_v2 extends LongPressAwareTableDataAdapter<PassBookEntryV2> {

    private static final int TEXT_SIZE = 14;

    public Pass_Book_TableView_Data_Adapter_v2(final Context context, final List<PassBookEntryV2> data, final TableView<PassBookEntryV2> tableView) {

        super(context, data, tableView);
    }

    @Override
    public View getDefaultCellView(int rowIndex, int columnIndex, ViewGroup parentView) {

        final PassBookEntryV2 passBookEntryV2 = getRowData(rowIndex);

        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderString(DateUtils1.normalDateTimeShortYearFormat.format(passBookEntryV2.getInsertionDate()));
                break;
            case 1:
                renderedView = renderString(passBookEntryV2.getParticulars());
                break;
            case 2:
                renderedView = renderString(String.valueOf(passBookEntryV2.getSecondAccountName()));
                break;
            case 3:
                renderedView = renderString(passBookEntryV2.getCreditAmount() == 0 ? "" : String.valueOf(passBookEntryV2.getCreditAmount()));
                break;
            case 4:
                renderedView = renderString(passBookEntryV2.getDebitAmount() == 0 ? "" : String.valueOf(passBookEntryV2.getDebitAmount()));
                break;
            case 5:
                renderedView = renderString(String.valueOf(DoubleUtils.roundOff_to_two_positions(passBookEntryV2.getBalance())));
                break;
        }

        return renderedView;
    }

    @Override
    public View getLongPressCellView(int rowIndex, int columnIndex, ViewGroup parentView) {

        return getDefaultCellView(rowIndex, columnIndex, parentView);
    }

    private View renderString(final String value) {

        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setTextColor(BLACK);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }

}
