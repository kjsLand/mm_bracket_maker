package mm_bracket_maker;
import java.util.Comparator;

// Used to compare the order of Team objects. 
public class OrderComparator implements Comparator<Team>{
    @Override
    public int compare(Team first, Team second) {
        return first.getOrder() - second.getOrder();
    }
}