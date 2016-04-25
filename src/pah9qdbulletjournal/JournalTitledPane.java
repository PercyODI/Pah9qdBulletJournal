/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pah9qdbulletjournal;

import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;

/**
 *
 * @author pears
 */
public class JournalTitledPane extends TitledPane{
    private Journal journal;

    JournalTitledPane(String name, ListView<Page> listView) {
        super(name, listView);
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }
}
