import {useRouter} from "vue-router";
import {ref, reactive, toRefs,} from 'vue'

// function toPagePath(url: string): void {
//     router.push({
//         path: url,
//     });
//     return undefined
// }
//

let globalValue = 1
let login = {
    loginState: true,
}

export {
    login
}