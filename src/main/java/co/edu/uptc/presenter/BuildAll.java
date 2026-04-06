package co.edu.uptc.presenter;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.interfaces.InterfaceCollection;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.AccountingCollection;
import co.edu.uptc.model.list.DoubleList;
import co.edu.uptc.model.ManagerCollection;
import co.edu.uptc.model.utils.ValidateAccounting;
import co.edu.uptc.model.utils.ValidatePerson;
import co.edu.uptc.model.utils.ValidateProduct;
import co.edu.uptc.persistence.csv.imp.AccountingCSV;
import co.edu.uptc.persistence.csv.imp.PersonCSV;
import co.edu.uptc.persistence.csv.imp.ProductCSV;
import co.edu.uptc.pojo.Accounting;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.view.Menu;
import co.edu.uptc.view.ViewAccounting;
import co.edu.uptc.view.ViewPerson;
import co.edu.uptc.view.ViewProduct;


public class BuildAll {
    Menu menu = new Menu();
    InterfaceCollection<Person> queuePerson;
    InterfaceCollection<Product> stackProduct;
    InterfaceCollection<Accounting> collectionAccounting;
    public void buildPerson(){
        PresenterGlobal<Person> presenterPerson = new PresenterGlobal<Person>();
         queuePerson = new ManagerCollection<>(DoubleList::addLast, DoubleList::removeFirst);
        presenterPerson.setCollection(queuePerson);
        queuePerson.setValidator(new ValidatePerson());
        queuePerson.setCsv(new PersonCSV(), AppConfig.getProperty("output.people"));
        presenterPerson.setModel(queuePerson);
        ViewInterface<Person> view = new ViewPerson();
        view.setPresenter(presenterPerson);
        menu.addView(view);

    }

    public void buildProduct(){
        PresenterGlobal<Product> presenterProduct = new PresenterGlobal<Product>();
        stackProduct = new ManagerCollection<>(DoubleList::addFirst, DoubleList::removeFirst);
        presenterProduct.setCollection(stackProduct);
        stackProduct.setValidator(new ValidateProduct());
        stackProduct.setCsv(new ProductCSV(), AppConfig.getProperty("output.product"));
        presenterProduct.setModel(stackProduct);
        ViewInterface<Product> viewProduct = new ViewProduct();
        viewProduct.setPresenter(presenterProduct);
        menu.addView(viewProduct);


    }
    public void buildAccounting(){
        PresenterGlobal<Accounting> presenterAccounting = new PresenterGlobal<Accounting>();
        collectionAccounting = new AccountingCollection();
        presenterAccounting.setCollection(collectionAccounting);
        collectionAccounting.setValidator(new ValidateAccounting());
        collectionAccounting.setCsv(new AccountingCSV(), AppConfig.getProperty("output.accounting"));
        presenterAccounting.setModel(collectionAccounting);
        ViewInterface<Accounting> viewAccounting = new ViewAccounting();
        viewAccounting.setPresenter(presenterAccounting);
        menu.addView(viewAccounting);


    }
    public void start(){
        buildPerson();
        buildProduct();
        buildAccounting();
        menu.start();
    }

}
