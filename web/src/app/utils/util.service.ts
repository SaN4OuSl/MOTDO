import {Injectable} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  list_of_items: number[][] = [];

  public userForm: FormGroup = new FormGroup({

    amount_wood: new FormControl('', Validators.required),
    amount_cloth: new FormControl('', Validators.required),
    amount_people_power: new FormControl('', Validators.required),
    price_shaf: new FormControl('', Validators.required),
    price_kris: new FormControl('', Validators.required),
    price_lig: new FormControl('', Validators.required),
    price_div: new FormControl('', Validators.required),
    a00: new FormControl('', Validators.required),
    a01: new FormControl('', Validators.required),
    a02: new FormControl('', Validators.required),
    a03: new FormControl('', Validators.required),
    a10: new FormControl('', Validators.required),
    a11: new FormControl('', Validators.required),
    a12: new FormControl('', Validators.required),
    a13: new FormControl('', Validators.required),
    a20: new FormControl('', Validators.required),
    a21: new FormControl('', Validators.required),
    a22: new FormControl('', Validators.required),
    a23: new FormControl('', Validators.required),
    type_of_function: new FormControl('-->max', Validators.required),
    type_of_shaf: new FormControl('>=', Validators.required),
    type_of_kris: new FormControl('>=', Validators.required),
    type_of_lig: new FormControl('>=', Validators.required),
    type_of_div: new FormControl('>=', Validators.required),
    amount_of_shaf: new FormControl('', Validators.required),
    amount_of_kris: new FormControl('', Validators.required),
    amount_of_lig: new FormControl('', Validators.required),
    amount_of_div: new FormControl('', Validators.required),
  });

  get getAddData() {
    return this.userForm.controls;
  }

  constructor() {
  }
}
