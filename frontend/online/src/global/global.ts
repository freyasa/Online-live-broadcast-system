import {useRouter} from "vue-router";
import {ref, reactive, toRefs,} from 'vue'
import User from "./vo/User";


let login = {
    loginState: true,
    user: {

    }
}

login.user = new User();

export {
    login
}