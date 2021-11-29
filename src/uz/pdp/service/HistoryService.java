package uz.pdp.service;

import uz.pdp.model.History;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HistoryService implements BaseService<String, History, List<History>>{

    List<History> historyList = new ArrayList<>();

    @Override
    public boolean add(History history) {
        historyList.add(history);
        return true;
    }

    @Override
    public History check(String s) {
        return null;
    }

    @Override
    public History check(String t1, String t2) {
        return null;
    }

    @Override
    public List<History> list(UUID id) {
        List<History> histories = new ArrayList<>();
        for(History history: historyList) {
            if(history.getUserId().equals(id))
                histories.add(history);
        }
        return histories;
    }
}
