import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import java.util.Random;

@ManagedBean(name = "formBean")
@SessionScoped
public class FormBean {

    private String name;
    private String email;
    private String age;
    private boolean male;
    private String education;
    private int height;

    //female
    private int breast;
    private String cup;
    private int waistF;
    private int hip;

    //male
    private int cheast;
    private int waistM;

    private String money;
    private String periodicity;
    private String[] color;
    private String[] clothes;

    private int counter;
    private String advertisement;
    private String[] advertisements = {"https://pracadyplomowa.info/images/zdjecia/promocja.jpg",
            "https://www.templatka.pl/zdjecia/reklama.jpg"};

    public FormBean() {
        advertisement = advertisements[0];
    }



    //gettery i settery

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBreast() {
        return breast;
    }

    public void setBreast(int breast) {
        this.breast = breast;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public int getWaistF() {
        return waistF;
    }

    public void setWaistF(int waistF) {
        this.waistF = waistF;
    }

    public int getHip() {
        return hip;
    }

    public void setHip(int hip) {
        this.hip = hip;
    }

    public int getCheast() {
        return cheast;
    }

    public void setCheast(int cheast) {
        this.cheast = cheast;
    }

    public int getWaistM() {
        return waistM;
    }

    public void setWaistM(int waistM) {
        this.waistM = waistM;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    public String[] getClothes() {
        return clothes;
    }

    public void setClothes(String[] clothes) {
        this.clothes = clothes;
    }

    public String getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(String advertisement) {
        this.advertisement = advertisement;
    }

    //gettery i settery - koniec

    public void checkHeight(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Integer height = (Integer) o;
        if (male) {
            if (height < 165 || height > 200) {
                ((UIInput) uiComponent).setValid(true);
                FacesMessage facesMessage = new FacesMessage("Nie poprawne dane");
                facesContext.addMessage(uiComponent.getClientId(facesContext), facesMessage);
            }
        } else {
            if (height < 150 || height> 185) {
                ((UIInput) uiComponent).setValid(false);
                FacesMessage facesMessage = new FacesMessage("Nie poprawne dane");
                facesContext.addMessage(uiComponent.getClientId(facesContext), facesMessage);
            }
        }
    }

    public boolean checkLengthOfClothes() {
        return clothes.length != 0;
    }

    public boolean checkLengthOfColors() {
        return color.length != 0;
    }

    public void incrementCounter() {
         counter++;
        changeCurrentAdvertisement();
        System.out.println(counter);
    }

    private void changeCurrentAdvertisement() {
        Random r = new Random();
        int max = advertisements.length;
        this.advertisement = this.advertisements[r.nextInt(max)];
    }

    public String endFirstPart() {
        changeCurrentAdvertisement();
        return "formPartTwo";
    }

    public String endSecondPart() {
        changeCurrentAdvertisement();
        return "summary";
    }

    public String startAgain() {
        changeCurrentAdvertisement();
        return "index";
    }

}
