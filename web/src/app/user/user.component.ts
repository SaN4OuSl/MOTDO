import {Component, OnInit} from '@angular/core';
import {UtilService} from "../utils/util.service";
import {MatTableDataSource} from "@angular/material/table";
import {baseUrl} from "../../environments/environment";

export interface Furniture {
  name: string;
  costOfFurniture: string;
}

const ELEMENT_DATA: Furniture[] = [
  {name: 'Дерево', costOfFurniture: ''},
  {name: 'Тканина', costOfFurniture: ''},
  {name: 'Трудові ресурси', costOfFurniture: ''},
];

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  displayedColumns: string[] = ['name', 'Шафа', 'Крісло', 'Ліжко', 'Дивани'];
  columnsToDisplay: string[] = this.displayedColumns.slice();
  data = new MatTableDataSource(ELEMENT_DATA);
  utilService: UtilService
  errorMessage: string = "";
  money: number | undefined;
  amountOfShaf: number = 0;
  amountOfKris: number = 0;
  amountOfLig: number = 0;
  amountOfDiv: number = 0;
  minCosts: number | undefined;
  minCostsForWork: number | undefined;
  messageWithRecommendation: string = "";
  coefsOfUnivers: string | undefined;

  readonly shaf = 'x1'
  readonly kris = 'x2'
  readonly lig = 'x3'
  readonly div = 'x4'

  constructor(utilService: UtilService) {
    this.utilService = utilService;
  }


  ngOnInit(): void {
  }

  async sendDataToCalculate() {
    if (this.utilService.userForm.valid) {
      this.errorMessage = "";
      let dataForm = this.utilService.userForm.value
      let costFunction = dataForm['price_shaf'] + this.shaf + "+" + dataForm['price_kris'] + this.kris
        + "+" + dataForm['price_lig'] + this.lig + "+" + dataForm['price_div'] + this.div + dataForm['type_of_function'];
      let limitaions: string[] = [];
      limitaions.push(dataForm['a00'] + this.shaf + "+" + dataForm['a01'] + this.kris
        + "+" + dataForm['a02'] + this.lig + "+" + dataForm['a03'] + this.div + "<=" + dataForm['amount_wood']);
      limitaions.push(dataForm['a10'] + this.shaf + "+" + dataForm['a11'] + this.kris
        + "+" + dataForm['a12'] + this.lig + "+" + dataForm['a13'] + this.div + "<=" + dataForm['amount_cloth']);
      limitaions.push(dataForm['a20'] + this.shaf + "+" + dataForm['a21'] + this.kris
        + "+" + dataForm['a22'] + this.lig + "+" + dataForm['a23'] + this.div + "<=" + dataForm['amount_people_power']);
      limitaions.push(this.shaf + dataForm['type_of_shaf'] + dataForm['amount_of_shaf']);
      limitaions.push(this.kris + dataForm['type_of_kris'] + dataForm['amount_of_kris']);
      limitaions.push(this.lig + dataForm['type_of_lig'] + dataForm['amount_of_lig']);
      limitaions.push(this.div + dataForm['type_of_div'] + dataForm['amount_of_div']);
      let finalRequest = "{ \"costFunction\":\"" + costFunction +
        "\",\"limitations\":[\"" +
        limitaions[0] + "\",\"" +
        limitaions[1] + "\",\"" +
        limitaions[2] + "\",\"" +
        limitaions[3] + "\",\"" +
        limitaions[4] + "\",\"" +
        limitaions[5] + "\",\"" +
        limitaions[6] + "\"" +
        "]}"
      console.log(finalRequest)
      let result = await fetch(`${baseUrl}symplex`,
        {
          method: 'POST',
          headers: {
            'Content-type': 'application/json',
          },
          body: finalRequest
        }).then(r => r.json())
      console.log(result)
      if (dataForm['type_of_function'] == '-->max') {
        this.money = Math.round(result.items['max F']);
      } else {
        this.money = Math.round(result.items['min F']);
      }
      this.amountOfShaf = Math.round(result.items[this.shaf]);
      this.amountOfKris = Math.round(result.items[this.kris]);
      this.amountOfLig = Math.round(result.items[this.lig]);
      this.amountOfDiv = Math.round(result.items[this.div]);
    } else {
      this.errorMessage = "Заповніть усі поля"
    }
  }

  addColumn() {
    this.columnsToDisplay.push(this.displayedColumns[1]);
  }

  removeColumn() {
    if (this.columnsToDisplay.length) {
      this.columnsToDisplay.pop();
    }
  }

  async transportDataCalculate() {
    if (this.utilService.transportForm.valid) {
      let dataForm = this.utilService.transportForm.value;
      let a = "\"a\": [" + dataForm['a1'] + ", " + dataForm['a2'] + ", " + dataForm['a3'] + "]";
      let b = "\"b\": [" + dataForm['b1'] + ", " + dataForm['b2'] + ", " + dataForm['b3'] + ", " + dataForm['b4'] + "]";
      let c = "\"c\": [[" + dataForm['a1b1'] + ", " + dataForm['a1b2'] + ", " + dataForm['a1b3'] + ", " + dataForm['a1b4'] + "]," +
        "[" + dataForm['a2b1'] + ", " + dataForm['a2b2'] + ", " + dataForm['a2b3'] + ", " + dataForm['a2b4'] + "]," +
        "[" + dataForm['a3b1'] + ", " + dataForm['a3b2'] + ", " + dataForm['a3b3'] + ", " + dataForm['a3b4'] + "]]";
      let finalRequest = "{" + a + "," + b + "," + c + "}"
      let result = await fetch(`${baseUrl}transport`,
        {
          method: 'POST',
          headers: {
            'Content-type': 'application/json',
          },
          body: finalRequest
        }).then(r => r.json())
      console.log(result)
      let resultTable = result.c;
      this.minCosts = result.resultFunction;
      for (let i = 0; i < resultTable.length; i++) {
        let n = i + 1;
        this.messageWithRecommendation += "З складу №" + n + " відправити у "
        for (let j = 0; j < resultTable[i].length; j++) {
          if (resultTable[i][j] > 0) {
            let m = j + 1;
            this.messageWithRecommendation += "магазин №" + m + ": " + resultTable[i][j] + ", "
          }
          this.messageWithRecommendation += "\n"
        }
      }
    } else {
      this.messageWithRecommendation = 'Не всі поля заповнені'
    }
  }

  async appointmentDataCalculate() {
    if (this.utilService.appointmentForm.valid) {
      let dataForm = this.utilService.appointmentForm.value;
      let cost = "\"cost\": [[" + dataForm['a1b2'] + ", " + dataForm['a1b2'] + ", " + dataForm['a1b3'] + "],["
        + dataForm['a2b1'] + ", " + dataForm['a2b2'] + ", " + dataForm['a2b3'] + "],["
        + dataForm['a3b1'] + ", " + dataForm['a3b2'] + ", " + dataForm['a3b3'] + "]]";
      let finalRequest = "{" + cost + "}"
      let result = await fetch(`${baseUrl}appointment`,
        {
          method: 'POST',
          headers: {
            'Content-type': 'application/json',
          },
          body: finalRequest
        }).then(r => r.json())
      console.log(result)
      this.minCostsForWork = result.resultFunction;
    } else {
      this.messageWithRecommendation = 'Не всі поля заповнені'
    }
  }

  async hiearchyDataCalculate() {
    if (this.utilService.hierarchyForm.valid) {
      let dataForm = this.utilService.hierarchyForm.value;
      let criteria = "\"criteria\": [[" + dataForm['coef'] + "]]";
      let variants = "\"variants\": [[" + dataForm['a1b2'] + ", " + dataForm['a1b2'] + ", " + dataForm['a1b3'] + "],["
        + dataForm['a2b1'] + ", " + dataForm['a2b2'] + ", " + dataForm['a2b3'] + "]]";
      let finalRequest = "{" + criteria + "," + variants + "}"
      let result = await fetch(`${baseUrl}analysishierarchy`,
        {
          method: 'POST',
          headers: {
            'Content-type': 'application/json',
          },
          body: finalRequest
        }).then(r => r.json())
      console.log(result)
      let i = 0;
      let maxelement = 0;
      let idOfElement = 0;
      let finalMessage = "";
      for (let res of result.results) {
        i++;
        if (res > maxelement) {
          maxelement = res
          idOfElement = i;
        }
        finalMessage += i + "й уніерситет: " + res + '\n'
      }
      finalMessage += "Краще всього поступати в " + idOfElement + "й університет"
      this.coefsOfUnivers = finalMessage;
    } else {
      this.messageWithRecommendation = 'Не всі поля заповнені'
    }
  }
}
