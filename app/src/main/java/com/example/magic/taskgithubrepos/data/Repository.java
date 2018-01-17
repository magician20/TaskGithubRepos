package com.example.magic.taskgithubrepos.data;


/**
 * Created by Magician on 12/24/2017.
 * describe the data
 */

public class Repository {
    private String name;
    private String description;
    private String owner;
    private boolean fork;
    private String repo_url_string;
    private String owner_url_string;

//  //another way to build Repository
//    static class Builder {
//        private String name;
//        private String description;
//        private String owner;
//        private boolean fork;
//        private String repoUrlString;
//        private String ownerUrlString;
//
//        Builder withName(String name) {
//            this.name = name;
//            return this;
//        }
//
//        Builder withDescription(String description) {
//            this.description = description;
//            return this;
//        }
//
//        Builder withOwner(String owner) {
//            this.owner = owner;
//            return this;
//        }
//
//        Builder withFork(boolean fork) {
//            this.fork = fork;
//            return this;
//        }
//
//        Builder withRepoUrlString(String repoUrlString) {
//            this.repoUrlString = repoUrlString;
//            return this;
//        }
//
//        Builder withOwnerUrlString(String ownerUrlString) {
//            this.ownerUrlString = ownerUrlString;
//            return this;
//        }
//
//        Repository build() {
//            return new Repository(name, description, owner, fork, repoUrlString, ownerUrlString);
//        }
//
//    }

    public Repository(String name, String description, String owner, boolean fork,
                      String repo_url_string, String owner_url_string) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.fork = fork;
        this.repo_url_string = repo_url_string;
        this.owner_url_string = owner_url_string;
    }

    public boolean isFork() {
        return fork;
    }

    public String getName() {
        return name;
    }

    public String getRepo_url_string() {
        return repo_url_string;
    }

    public String getOwner_url_string() {
        return owner_url_string;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }


    //for test data
    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", fork='" + fork + '\'' +
                ", repoUrlString='" + repo_url_string + '\'' +
                ", ownerUrlString='" + owner_url_string + '\'' +
                '}';
    }
}
