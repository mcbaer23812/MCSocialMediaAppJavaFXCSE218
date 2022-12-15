This text file discusses all data structures used throughout the project.
The "model" package contains all of my data structures and storage.

USER ACCOUNTS - I used a TreeMap for storing user data in UserData.java. The key value pair provided by a map
was crucial in my implementation for this project. I used the username as the key, and the user account as the
value. This allowed me to pull a user account from anywhere, as I have a value for a user that is currently
logged in. TreeMap is also incredibly useful in data retrieval, as user accounts are searched for on both
the signup page and the login page. Searching with a TreeMap is O(logn), as it is backed by a Red-Black tree
which is self balancing and uses binary search, which I thought was perfect for this.

POSTS - I used linked lists for storing allPosts in UserData.java, userPosts in User.java, and replies in Post.java.
I chose linked list because you can add an infinite number of data items without ever needing to change the size of 
the list, and insertion can be done in constant time O(1). The linked list API is especially useful for posts as its 
implementation is a double linked list. The double ended nature allows me to easily show the oldest post first or the newest post 
first, which are both required in this project in different functions as according to the outline.

FOLLOWING/SUBSCRIBERS - I used TreeSet for the list of accounts that a user can be following in User.java. I chose TreeSet
as it is based on a Red-Black tree, which has O(logn) efficiency for everything, since a red black tree is self balancing.
I figured it would be most useful to use TreeSet as it preserves order (implements SortedSet), so if you were to 
implement a following list feature where a user can see who they are following, they can see that list in order of 
who they followed first without having to check a date/time for each one first.

DICTIONARY - I used HashSet for the dictionary. Since a dictionary contains a very vast amount of strings, the constant O(1)
complexity behind the .contains() method in a HashSet is unbeatable. All dictionary data gets copied over to the HashSet
from dictionary.txt as soon as the first UserData instance (the data center) is created. This data structure allows for
very efficient spell checking, as you can call .contains() for every word a user types.

