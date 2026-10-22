package com.example.accessing_data_jpa;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESS_BOOK")
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BuddyInfo> buddies;

    public AddressBook() {buddies = new ArrayList<>();}

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public void addBuddy(BuddyInfo buddy) {buddies.add(buddy);}

    public void removeBuddy(BuddyInfo buddy) {buddies.remove(buddy);}

    public BuddyInfo getBuddy(int index) {return buddies.get(index);}

    public int getSize() {return buddies.size();}

    public List<BuddyInfo> getBuddies() { return buddies; }

    public void setBuddies(List<BuddyInfo> buddies) { this.buddies = buddies; }

    @Override
    public String toString() {return "AddressBook ID: " + id + " -> " + buddies.toString();}
}