/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name)) {
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (this.getUser(name) != null) {
            System.out.println(name+" Already exits in this Network");
            return false;
        }
        if (userCount == users.length) {
            System.out.println("Can not add the Network is full");
            return false;
        }
        User newUser = new User(name);
        users[userCount] = newUser;
        userCount++;
        System.out.println("Successfully added "+name+" to the Network");
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (this.getUser(name1) == null) {
            System.out.println("The User "+name1+" is not in this Network");
            return false;
        }
        if (this.getUser(name2) == null) {
            System.out.println("The User "+name2+" is not in this Network");
            return false;
        }
        return this.getUser(name1).addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        if (this.getUser(name) == null) {return null;}
        String mostRec = null;
        int maxMutual = 0;
        for (int i = 0; i < this.userCount; i++) {
            if (this.users[i] == this.getUser(name)) {
                continue;
            }
            int currentMutual = this.getUser(name).countMutual(this.users[i]);
            if (currentMutual > maxMutual) {
                maxMutual = currentMutual;
                mostRec = this.users[i].getName();
            }
        }
        return mostRec;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int mostPopularCount = 0;
        String mostPopular = null;
        for (int i = 0; i < this.userCount; i++) {
            int currentUserCount = followeeCount(this.users[i].getName());
            if (currentUserCount > mostPopularCount) {
                mostPopular = this.users[i].getName();
                mostPopularCount = currentUserCount;
            }
        }
        return mostPopular;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < this.userCount; i++) {
            if (this.users[i].follows(name)) {
                count++;
            }
        }
        return count;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String ans = "Network:\n";
        for (int i = 0; i < this.userCount; i++) {
            ans = ans + this.users[i] + "\n";
        }
        return ans;
    }
}
