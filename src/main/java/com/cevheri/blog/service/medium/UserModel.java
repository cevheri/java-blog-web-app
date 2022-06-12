package com.cevheri.blog.service.medium;

import java.io.Serializable;
import java.util.Objects;

public class UserModel extends AbstractModel implements Serializable {
    private String id;
    private String username;
    private String name;
    private String url;
    private String imageUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        if (id != null ? !id.equals(userModel.id) : userModel.id != null) return false;
        if (username != null ? !username.equals(userModel.username) : userModel.username != null) return false;
        if (name != null ? !name.equals(userModel.name) : userModel.name != null) return false;
        if (url != null ? !url.equals(userModel.url) : userModel.url != null) return false;
        return imageUrl != null ? imageUrl.equals(userModel.imageUrl) : userModel.imageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserModel{" +
            "id='" + id + '\'' +
            ", username='" + username + '\'' +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            '}';
    }
}
