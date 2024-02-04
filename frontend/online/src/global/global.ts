import {useRouter} from "vue-router";
import {ref, reactive, toRefs,} from 'vue'
import User from "./vo/User";


let login = {
    loginState: false,
    user: {

    }
}

login.user = new User();
export {
    login
}