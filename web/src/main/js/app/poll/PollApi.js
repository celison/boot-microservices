import axios from 'axios'

const rootURL = '/v1/poll/';

export async function getQuestions() {
    return await Promise.resolve(axios.get(rootURL).then(res => res.data));
}

// export async function postTask(task) {
//     return await axios.post(rootURL, task);
// }
//
// export async function putTask(task) {
//     return await axios.put(rootURL, task);
// }
//
// export async function deleteTask(task) {
//     return await axios.delete(rootURL + task.id);
// }