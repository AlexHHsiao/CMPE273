import {baseUrl} from "../../utils/constants/API";
import {fetchHelper} from "../../utils/helpers/API";

export const getAllUser = () => fetchHelper(`${baseUrl}users`, "GET");

export const deleteUser = (id) => fetchHelper(`${baseUrl}users/${id}`, "DELETE");
