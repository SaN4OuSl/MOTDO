import {Injectable} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class UtilService {

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

  public transportForm: FormGroup = new FormGroup({

    a1b1: new FormControl('', Validators.required),
    a1b2: new FormControl('', Validators.required),
    a1b3: new FormControl('', Validators.required),
    a1b4: new FormControl('', Validators.required),
    a2b1: new FormControl('', Validators.required),
    a2b2: new FormControl('', Validators.required),
    a2b3: new FormControl('', Validators.required),
    a2b4: new FormControl('', Validators.required),
    a3b1: new FormControl('', Validators.required),
    a3b2: new FormControl('', Validators.required),
    a3b3: new FormControl('', Validators.required),
    a3b4: new FormControl('', Validators.required),
    a1: new FormControl('', Validators.required),
    a2: new FormControl('', Validators.required),
    a3: new FormControl('', Validators.required),
    b1: new FormControl('', Validators.required),
    b2: new FormControl('', Validators.required),
    b3: new FormControl('', Validators.required),
    b4: new FormControl('', Validators.required),
  });

  public appointmentForm: FormGroup = new FormGroup({

    a1b1: new FormControl('', Validators.required),
    a1b2: new FormControl('', Validators.required),
    a1b3: new FormControl('', Validators.required),
    a2b1: new FormControl('', Validators.required),
    a2b2: new FormControl('', Validators.required),
    a2b3: new FormControl('', Validators.required),
    a3b1: new FormControl('', Validators.required),
    a3b2: new FormControl('', Validators.required),
    a3b3: new FormControl('', Validators.required),
  });

  constructor() {
  }
}
